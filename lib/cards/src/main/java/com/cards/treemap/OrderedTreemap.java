package com.cards.treemap;

import java.util.ArrayList;

import android.graphics.RectF;

public class OrderedTreemap  {
	
	/** Type of algorithm used to retrieve rectangles */
	public enum Pivot {
		PIVOT_SIZE, PIVOT_MIDDLE, PIVOT_BIGGEST
	}

	/** Pivot type */
	private Pivot pivotType;

	public OrderedTreemap(Pivot pivotType) {
		this.pivotType = pivotType;
	}

	/** Empty constructor */
	public OrderedTreemap() {
		pivotType = Pivot.PIVOT_MIDDLE;
	}

	/** Set the type of algorithm to apply */
	public void setPivotType(Pivot pivotType) {
		this.pivotType = pivotType;
	}

	/**
	 * 
	 * @param items
	 * @param width
	 * @param height
	 * @return
	 */
	public ArrayList<RectF> getRects(ArrayList<Integer> items, int width, int height) {
		double totalSize = 0;
		double area = width * height;

		// To find total size of the items to compare the aspect ratio of the
		// card
		for (int i = 0; i < items.size(); i++) {
			totalSize += items.get(i);
		}

		double scaleFactor = Math.sqrt(area / totalSize);
		Rectangle box = new Rectangle(0, 0, width, height);
		box.x /= scaleFactor;
		box.y /= scaleFactor;
		box.w /= scaleFactor;
		box.h /= scaleFactor;

		double[] sizes = new double[items.size()];
		for (int i = 0; i < items.size(); i++) {
			sizes[i] = items.get(i);
		}
		Rectangle[] results = orderedRecurseForRects(sizes, box);

		ArrayList<RectF> rects = new ArrayList<RectF>();
		for (Rectangle rect : results) {
			double x = (rect.x * scaleFactor);
			double y = (rect.y * scaleFactor);
			double w = (rect.w * scaleFactor);
			double h = (rect.h * scaleFactor);
			rects.add(new RectF((float) x, (float) y, (float) (x + w), (float) (y + h)));
		}
		return rects;
	}

	private int getPivotIndex(double[] sizes) {
		int index = 0;
		double leftSize, rightSize;
		double ratio;
		double bestRatio = 0;
		double biggest;
		boolean first = true;
		switch (pivotType) {
			case PIVOT_MIDDLE:
				index = (sizes.length - 1) / 2;
				break;
			case PIVOT_SIZE:
				leftSize = 0;
				rightSize = computeSize(sizes);

				for (int i = 0; i < sizes.length; i++) {
					ratio = Math.max(((double) leftSize / rightSize), ((double) rightSize / leftSize));
					if (first || (ratio < bestRatio)) {
						first = false;
						bestRatio = ratio;
						index = i;
					}

					leftSize += sizes[i];
					rightSize -= sizes[i];
				}
				break;
			case PIVOT_BIGGEST:
				biggest = 0;
				for (int i = 0; i < sizes.length; i++) {
					if (first || (sizes[i] > biggest)) {
						first = false;
						biggest = sizes[i];
						index = i;
					}
				}
				break;
		}
		return index;
	}

	private double computeSize(double[] sizes) {
		double size = 0;
		for (int i = 0; i < sizes.length; i++) {
			size += sizes[i];
		}
		return size;
	}

	private double computeSize(double[] sizes, int i1, int i2) {
		double size = 0;
		for (int i = i1; i <= i2; i++) {
			size += sizes[i];
		}

		return size;
	}

	private Rectangle[] orderedRecurseForRects(double[] sizes, Rectangle box) {
		// Declaring values needed in this method
		int pivotIndex = getPivotIndex(sizes);
		int i;
		double[] l1 = null;
		double[] l2 = null;
		double[] l3 = null;
		double l1Size = 0;
		double l2Size = 0;
		double l3Size = 0;
		Rectangle r1 = null;
		Rectangle r2 = null;
		Rectangle r3 = null;
		Rectangle rp = null;
		double pivotSize = sizes[pivotIndex];
		double pivotAR;
		Rectangle[] boxes = null;
		double totalViewAR;
		Rectangle box2;
		int d;
		double w, h;
		double ratio;

		// Stopping condition
		totalViewAR = box.w / box.h;
		d = sizes.length - pivotIndex - 1;

		if (sizes.length == 1) {
			boxes = new Rectangle[1];
			boxes[0] = box;
			return boxes;
		} else if (sizes.length == 2) {
			boxes = new Rectangle[2];
			ratio = sizes[0] / (sizes[0] + sizes[1]);
			if (totalViewAR >= 1) {
				w = ratio * box.w;
				boxes[0] = new Rectangle(box.x, box.y, w, box.h);
				boxes[1] = new Rectangle(box.x + w, box.y, box.w - w, box.h);
				debug("A: b0=" + boxes[0] + ", b1=" + boxes[1]);
			} else {
				h = ratio * box.h;
				boxes[0] = new Rectangle(box.x, box.y, box.w, h);
				boxes[1] = new Rectangle(box.x, box.y + h, box.w, box.h - h);
				debug("s0=" + sizes[0] + ", s1=" + sizes[1] + ", ratio=" + ratio + ", h=" + h + ", height=" + box.h);
				debug("B: b0=" + boxes[0] + ", b1=" + boxes[1]);
			}
			return boxes;
		}

		// First, compute R1
		l1 = new double[pivotIndex];
		System.arraycopy(sizes, 0, l1, 0, pivotIndex);
		l1Size = computeSize(l1);
		if (totalViewAR >= 1) {
			h = box.h;
			w = l1Size / h;
			r1 = new Rectangle(box.x, box.y, w, h);
			box2 = new Rectangle(r1.x + r1.w, box.y, box.w - r1.w, box.h);
		} else {
			w = box.w;
			h = l1Size / w;
			r1 = new Rectangle(box.x, box.y, w, h);
			box2 = new Rectangle(r1.x, r1.y + r1.h, box.w, box.h - r1.h);
		}

		if (d >= 3) {
			boolean first = true;
			double bestAR = 0;
			double bestW = 0;
			double bestH = 0;
			int bestIndex = 0;
			for (i = pivotIndex + 1; i < sizes.length; i++) {
				l2Size = computeSize(sizes, pivotIndex + 1, i);
				l3Size = computeSize(sizes, i + 1, sizes.length - 1);
				ratio = (double) (pivotSize + l2Size) / (pivotSize + l2Size + l3Size);
				if (totalViewAR >= 1) {
					w = ratio * box2.w;
					ratio = (double) pivotSize / (pivotSize + l2Size);
					h = ratio * box2.h;
				} else {
					h = ratio * box2.h;
					ratio = (double) pivotSize / (pivotSize + l2Size);
					w = ratio * box2.w;
				}
				pivotAR = w / h;
				if (first) {
					first = false;
					bestAR = pivotAR;
					bestW = w;
					bestH = h;
					bestIndex = i;
				} else if (Math.abs(pivotAR - 1) < Math.abs(bestAR - 1)) {
					bestAR = pivotAR;
					bestW = w;
					bestH = h;
					bestIndex = i;
				}
			}
			l2 = new double[bestIndex - pivotIndex];
			System.arraycopy(sizes, pivotIndex + 1, l2, 0, l2.length);
			if ((sizes.length - 1 - bestIndex) > 0) {
				l3 = new double[sizes.length - 1 - bestIndex];
				System.arraycopy(sizes, bestIndex + 1, l3, 0, l3.length);
			} else {
				l3 = null;
			}
			if (totalViewAR >= 1) {
				rp = new Rectangle(box2.x, box2.y, bestW, bestH);
				r2 = new Rectangle(box2.x, box2.y + bestH, bestW, box2.h - bestH);
				if (l3 != null) {
					r3 = new Rectangle(box2.x + bestW, box2.y, box2.w - bestW, box2.h);
				}
			} else {
				rp = new Rectangle(box2.x, box2.y, bestW, bestH);
				r2 = new Rectangle(box2.x + bestW, box2.y, box2.w - bestW, bestH);
				if (l3 != null) {
					r3 = new Rectangle(box2.x, box2.y + bestH, box2.w, box2.h - bestH);
				}
			}
		} else if (d > 0) {
			// l3 is null
			l2 = new double[d];
			debug("d=" + d + ", l2.len=" + l2.length);
			System.arraycopy(sizes, pivotIndex + 1, l2, 0, d);
			ratio = (double) pivotSize / (pivotSize + computeSize(l2));
			if (totalViewAR >= 1) {
				h = ratio * box2.h;
				rp = new Rectangle(box2.x, box2.y, box2.w, h);
				r2 = new Rectangle(box2.x, box2.y + h, box2.w, box2.h - h);
			} else {
				w = ratio * box2.w;
				rp = new Rectangle(box2.x, box2.y, w, box2.h);
				r2 = new Rectangle(box2.x + w, box2.y, box2.w - w, box2.h);
			}
		} else {
			rp = box2;
		}

		// Finally, recurse on sublists
		Rectangle[] l1boxes = null;
		Rectangle[] l2boxes = null;
		Rectangle[] l3boxes = null;
		int numBoxes = 0;

		if (l1.length > 1) {
			debug("Recurse R1");
			l1boxes = orderedRecurseForRects(l1, r1);
			debug("l1boxes.len = " + l1boxes.length);
		} else if (l1.length == 1) {
			l1boxes = new Rectangle[1];
			l1boxes[0] = r1;
			debug("l1boxes.len = " + l1boxes.length);
		}

		if (l2 != null) {
			if (l2.length > 1) {
				debug("Recurse R2");
				l2boxes = orderedRecurseForRects(l2, r2);
				debug("l2boxes.len = " + l2boxes.length);
			} else if (l2.length == 1) {
				l2boxes = new Rectangle[1];
				l2boxes[0] = r2;
				debug("l2boxes.len = " + l2boxes.length);
			}
		}

		if (l3 != null) {
			if (l3.length > 1) {
				debug("Recurse R3");
				l3boxes = orderedRecurseForRects(l3, r3);
				debug("l3boxes.len = " + l3boxes.length);
			} else if (l3.length == 1) {
				l3boxes = new Rectangle[1];
				l3boxes[0] = r3;
				debug("l3boxes.len = " + l3boxes.length);
			}
		}

		numBoxes = l1.length + 1;
		if (l2 != null) {
			numBoxes += l2.length;
		}
		if (l3 != null) {
			numBoxes += l3.length;
		}
		boxes = new Rectangle[numBoxes];
		i = 0;
		if (l1boxes != null) {
			System.arraycopy(l1boxes, 0, boxes, 0, l1boxes.length);
			i = l1boxes.length;
		}
		boxes[i] = rp;
		i++;
		if (l2 != null) {
			debug("l2 copy: i=" + i + ", boxes.len=" + boxes.length + ", l2.len=" + l2boxes.length);
			System.arraycopy(l2boxes, 0, boxes, i, l2boxes.length);
		}
		if (l3 != null) {
			i += l2boxes.length;
			debug("l3 copy: i=" + i + ", boxes.len=" + boxes.length + ", l3.len=" + l3boxes.length);
			System.arraycopy(l3boxes, 0, boxes, i, l3boxes.length);
		}

		for (i = 0; i < boxes.length; i++) {
			debug("boxes[" + i + "] = " + boxes[i]);
		}

		// Trying other ways to implement the algorithm
		// This is not mandatory to achieve what we want to achieve
		boxes = tryAlternativeLayouts(sizes, box, boxes);

		return boxes;
	}

	private Rectangle[] tryAlternativeLayouts(double[] sizes, Rectangle box, Rectangle[] layoutBoxes) {
		debug("Trying alternate algorithms ");
		Rectangle[] boxes = layoutBoxes;
		Rectangle[] nboxes = null;
		double ratio1, ratio2, ratio3, ratio4, ratio5;
		double w, h;
		double w1, w2, w3, w4, w5;
		double h1, h2, h3, h4, h5;
		double boxAR = box.w / box.h;
		double origAvgAR;
		double newAvgAR;

		if (sizes.length == 3) {
			// Try snake alg.
			nboxes = new Rectangle[3];
			ratio1 = (double) (sizes[0]) / (sizes[0] + sizes[1] + sizes[2]);
			ratio2 = (double) (sizes[1]) / (sizes[0] + sizes[1] + sizes[2]);
			ratio3 = (double) (sizes[2]) / (sizes[0] + sizes[1] + sizes[2]);
			if (boxAR >= 1) {
				h = box.h;
				w1 = ratio1 * box.w;
				w2 = ratio2 * box.w;
				w3 = ratio3 * box.w;
				nboxes[0] = new Rectangle(box.x, box.y, w1, h);
				nboxes[1] = new Rectangle(box.x + w1, box.y, w2, h);
				nboxes[2] = new Rectangle(box.x + w1 + w2, box.y, w3, h);
			} else {
				w = box.w;
				h1 = ratio1 * box.h;
				h2 = ratio2 * box.h;
				h3 = ratio3 * box.h;
				nboxes[0] = new Rectangle(box.x, box.y, w, h1);
				nboxes[1] = new Rectangle(box.x, box.y + h1, w, h2);
				nboxes[2] = new Rectangle(box.x, box.y + h1 + h2, w, h3);
			}

			origAvgAR = computeAverageAspectRatio(boxes);
			newAvgAR = computeAverageAspectRatio(nboxes);
			if (newAvgAR < origAvgAR) {
				boxes = nboxes;
			}
		}

		if (sizes.length == 4) {
			// Try quad alg.
			nboxes = new Rectangle[4];
			ratio1 = (double) (sizes[0] + sizes[1]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3]);
			if (boxAR >= 1) {
				w = ratio1 * box.w;
				ratio2 = (double) (sizes[0]) / (sizes[0] + sizes[1]);
				h = ratio2 * box.h;
				nboxes[0] = new Rectangle(box.x, box.y, w, h);
				nboxes[1] = new Rectangle(box.x, box.y + h, w, box.h - h);
				ratio2 = (double) (sizes[2]) / (sizes[2] + sizes[3]);
				h = ratio2 * box.h;
				nboxes[2] = new Rectangle(box.x + w, box.y, box.w - w, h);
				nboxes[3] = new Rectangle(box.x + w, box.y + h, box.w - w, box.h - h);
			} else {
				h = ratio1 * box.h;
				ratio2 = (double) (sizes[0]) / (sizes[0] + sizes[1]);
				w = ratio2 * box.w;
				nboxes[0] = new Rectangle(box.x, box.y, w, h);
				nboxes[1] = new Rectangle(box.x, box.y + h, w, box.h - h);
				ratio2 = (double) (sizes[2]) / (sizes[2] + sizes[3]);
				h = ratio2 * box.h;
				nboxes[2] = new Rectangle(box.x + w, box.y, box.w - w, h);
				nboxes[3] = new Rectangle(box.x + w, box.y + h, box.w - w, box.h - h);
			}

			origAvgAR = computeAverageAspectRatio(boxes);
			newAvgAR = computeAverageAspectRatio(nboxes);
			if (newAvgAR < origAvgAR) {
				boxes = nboxes;
			}

			// Then try 4 snake alg.
			nboxes = new Rectangle[4];
			ratio1 = (double) (sizes[0]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3]);
			ratio2 = (double) (sizes[1]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3]);
			ratio3 = (double) (sizes[2]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3]);
			ratio4 = (double) (sizes[3]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3]);
			if (boxAR >= 1) {
				h = box.h;
				w1 = ratio1 * box.w;
				w2 = ratio2 * box.w;
				w3 = ratio3 * box.w;
				w4 = ratio4 * box.w;
				nboxes[0] = new Rectangle(box.x, box.y, w1, h);
				nboxes[1] = new Rectangle(box.x + w1, box.y, w2, h);
				nboxes[2] = new Rectangle(box.x + w1 + w2, box.y, w3, h);
				nboxes[3] = new Rectangle(box.x + w1 + w2 + w3, box.y, w4, h);
			} else {
				w = box.w;
				h1 = ratio1 * box.h;
				h2 = ratio2 * box.h;
				h3 = ratio3 * box.h;
				h4 = ratio4 * box.h;
				nboxes[0] = new Rectangle(box.x, box.y, w, h1);
				nboxes[1] = new Rectangle(box.x, box.y + h1, w, h2);
				nboxes[2] = new Rectangle(box.x, box.y + h1 + h2, w, h3);
				nboxes[3] = new Rectangle(box.x, box.y + h1 + h2 + h3, w, h4);
			}

			origAvgAR = computeAverageAspectRatio(boxes);
			newAvgAR = computeAverageAspectRatio(nboxes);
			if (newAvgAR < origAvgAR) {
				boxes = nboxes;
			}
		}

		if (sizes.length == 5) {
			// Try 5 snake alg.
			nboxes = new Rectangle[5];
			ratio1 = (double) (sizes[0]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3] + sizes[4]);
			ratio2 = (double) (sizes[1]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3] + sizes[4]);
			ratio3 = (double) (sizes[2]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3] + sizes[4]);
			ratio4 = (double) (sizes[3]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3] + sizes[4]);
			ratio5 = (double) (sizes[4]) / (sizes[0] + sizes[1] + sizes[2] + sizes[3] + sizes[4]);
			if (boxAR >= 1) {
				h = box.h;
				w1 = ratio1 * box.w;
				w2 = ratio2 * box.w;
				w3 = ratio3 * box.w;
				w4 = ratio4 * box.w;
				w5 = ratio5 * box.w;
				nboxes[0] = new Rectangle(box.x, box.y, w1, h);
				nboxes[1] = new Rectangle(box.x + w1, box.y, w2, h);
				nboxes[2] = new Rectangle(box.x + w1 + w2, box.y, w3, h);
				nboxes[3] = new Rectangle(box.x + w1 + w2 + w3, box.y, w4, h);
				nboxes[4] = new Rectangle(box.x + w1 + w2 + w3 + w4, box.y, w5, h);
			} else {
				w = box.w;
				h1 = ratio1 * box.h;
				h2 = ratio2 * box.h;
				h3 = ratio3 * box.h;
				h4 = ratio4 * box.h;
				h5 = ratio5 * box.h;
				nboxes[0] = new Rectangle(box.x, box.y, w, h1);
				nboxes[1] = new Rectangle(box.x, box.y + h1, w, h2);
				nboxes[2] = new Rectangle(box.x, box.y + h1 + h2, w, h3);
				nboxes[3] = new Rectangle(box.x, box.y + h1 + h2 + h3, w, h4);
				nboxes[4] = new Rectangle(box.x, box.y + h1 + h2 + h3 + h4, w, h5);
			}

			origAvgAR = computeAverageAspectRatio(boxes);
			newAvgAR = computeAverageAspectRatio(nboxes);
			if (newAvgAR < origAvgAR) {
				boxes = nboxes;
			}
		}

		return boxes;
	}

	private double computeAverageAspectRatio(Rectangle[] rects) {
		double ar;
		double tar = 0;
		double w, h;
		int numRects = 0;
		for (int i = 0; i < rects.length; i++) {
			w = rects[i].w;
			h = rects[i].h;
			if ((w != 0) && (h != 0)) {
				ar = Math.max((w / h), (h / w));
				tar += ar;
				numRects++;
			}
		}
		tar /= numRects;
		return tar;
	}

	void debug(String str) {
		System.out.println(str);
	}

	class Rectangle {
		public double x, y, w, h;

		public Rectangle() {
			this(0, 0, 1, 1);
		}

		public Rectangle(Rectangle r) {
			setRect(r.x, r.y, r.w, r.h);
		}

		public Rectangle(double x, double y, double w, double h) {
			setRect(x, y, w, h);
		}

		public void setRect(double x, double y, double w, double h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public double aspectRatio() {
			return Math.max(w / h, h / w);
		}

		public double distance(Rectangle r) {
			return Math.sqrt((r.x - x) * (r.x - x) + (r.y - y) * (r.y - y) + (r.w - w) * (r.w - w) + (r.h - h)
					* (r.h - h));
		}

		public Rectangle copy() {
			return new Rectangle(x, y, w, h);
		}

		public String toString() {
			return "Rect: " + x + ", " + y + ", " + w + ", " + h;
		}

	}
}
