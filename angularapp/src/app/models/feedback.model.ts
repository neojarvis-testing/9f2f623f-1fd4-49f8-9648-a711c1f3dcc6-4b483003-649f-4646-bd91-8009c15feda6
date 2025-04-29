import { Food } from "./food.model";
import { User } from "./user.model";

export class Feedback {
    feedbackId?: number;
    feedbackText: string;
    date: Date;
    rating:number
    user?:User
    userId:number;
    foodId:number;
    food?:Food;
   }