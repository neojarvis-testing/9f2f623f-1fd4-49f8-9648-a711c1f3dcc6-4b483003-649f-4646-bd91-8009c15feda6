import { Food } from "./food.model";

export class Feedback {
    feedbackId?: number;
    feedbackText: string;
    date: Date;
    rating:number
    userId:number;
    foodId:number;
    food?:Food;
   }