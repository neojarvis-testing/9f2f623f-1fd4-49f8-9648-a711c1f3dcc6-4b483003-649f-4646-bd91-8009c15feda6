import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataset } from 'chart.js';
//import { Label } from 'ng2-charts';


@Component({
  selector: 'app-adminorderschart',
  templateUrl: './adminorderschart.component.html',
  styleUrls: ['./adminorderschart.component.css']
})
export class AdminorderschartComponent implements OnInit {

  public barChartOptions: ChartOptions = {
  responsive: true,
};
//public barChartLabels: Label[] = ['2021', '2022', '2023', '2024', '2025'];
public barChartLabels: string[] = ['2021', '2022', '2023', '2024', '2025'];
public barChartType: ChartType = 'bar';
public barChartLegend = true;
public barChartPlugins = [];

public barChartData: ChartDataset[] = [
  { data: [65, 59, 80, 81, 56], label: 'Pizza' },
  { data: [28, 48, 40, 19, 86], label: 'Burger' },
  { data: [18, 48, 77, 9, 100], label: 'Pasta' }
];

constructor() { }

ngOnInit(): void {
}
}

