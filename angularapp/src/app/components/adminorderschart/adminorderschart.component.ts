import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { OrderService } from 'src/app/services/order.service';
import { Orders } from 'src/app/models/orders.model';

@Component({
  selector: 'app-adminorderschart',
  templateUrl: './adminorderschart.component.html',
  styleUrls: ['./adminorderschart.component.css']
})
export class AdminorderschartComponent implements OnInit {
  @ViewChild('barChart', { static: true }) barChart: ElementRef<HTMLCanvasElement>;
  @ViewChild('lineChart', { static: true }) lineChart: ElementRef<HTMLCanvasElement>;

  barChartInstance: Chart;
  lineChartInstance: Chart;

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    Chart.register(...registerables); // Register all necessary components
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.orderService.getAllOrders().subscribe(
      (data: Orders[]) => {
        this.processOrders(data);
      },
      (error) => {
        console.error('Error fetching orders', error);
      }
    );
  }

  processOrders(orders: Orders[]): void {
    const ordersByDate = orders.reduce((acc, order) => {
      const date = new Date(order.orderDate).toLocaleDateString();
      acc[date] = (acc[date] || 0) + 1;
      return acc;
    }, {} as { [key: string]: number });

    const sortedDates = Object.keys(ordersByDate).sort((a, b) => new Date(a).getTime() - new Date(b).getTime());
    const sortedData = sortedDates.map(date => ordersByDate[date]);

    this.createBarChart(sortedDates, sortedData);
    this.createLineChart(sortedDates, sortedData);
  }

  createBarChart(labels: string[], data: number[]): void {
    this.barChartInstance = new Chart(this.barChart.nativeElement, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Orders',
            data: data,
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              stepSize: 1 // Set the step size to 1
            }
          }
        }
      }
    });
  }

  createLineChart(labels: string[], data: number[]): void {
    this.lineChartInstance = new Chart(this.lineChart.nativeElement, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Orders',
            data: data,
            backgroundColor: 'rgba(153, 102, 255, 0.2)',
            borderColor: 'rgba(153, 102, 255, 1)',
            borderWidth: 1,
            fill: true
          }
        ]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              stepSize: 1 // Set the step size to 1
            }
          }
        }
      }
    });
  }
}