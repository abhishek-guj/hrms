import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}



export function formatDate(date) {
  const strStartDate = date;
  strStartDate.setTime(
    strStartDate.getTime() - strStartDate.getTimezoneOffset() * 60000
  );
  strStartDate.toISOString();
  return strStartDate;
}

