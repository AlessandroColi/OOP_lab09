package it.unibo.oop.workers02;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadedSumMatrix implements SumMatrix {
	private final int nthread;

    /**
     * 
     * @param nthread
     *            no. of thread performing the sum.
     */
    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
    }

    private static class Worker extends Thread {
        private final List<Double> list;
        private final int startpos;
        private final int nelem;
        private double res;

        /**
         * Build a new worker.
         * 
         * @param list
         *            the list to sum
         * @param startpos
         *            the initial position for this worker
         * @param nelem
         *            the no. of elems to sum up for this worker
         */
        Worker(final List<Double> list) {
            super();
            this.list = list;
            this.startpos = 0;
            this.nelem = list.size();
        }

        @Override
        public void run() {
            for (int i = startpos; i < list.size() && i < startpos + nelem; i++) {
                this.res += this.list.get(i);
            }
        }

        /**
         * Returns the result of summing up the integers within the list.
         * 
         * @return the sum of every element in the array
         */
        public double getResult() {
            return this.res;
        }

    }

	@Override
	public double sum(double[][] matrix) {
		double ris=0;
		final int size = matrix.length % nthread + matrix.length / nthread;
		List<Worker> workers = new ArrayList<>();
		
		for (int i = 0 ; i < matrix.length ; i = i + size) {
			System.out.println("working from array " + i + "to array " + (i+size));
			List<Double> part = new ArrayList<>();
			for(int j = 0; j < size && i + j < matrix.length; j++) {
				for (int k = 0; k < matrix[i+j].length; k++) {
					part.add(matrix[i+j][k]);
				}
			}
			
			workers.add(new Worker(part));
		}
		for (final Worker w: workers) {
            w.start();
        }
		for (final Worker w: workers) {
            try {
                w.join();
                ris += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
		return ris;
	}

}
