package com.roima.hrms.enums;

public enum SlotBookingStatusEnum {
    Requested, // when booking slot requested untill last 15mins if priority > 0
    Waiting, // when already played and all
    Confirmed, // if no change untill last 15mins or if priority = 0 when booking
    Cancelled, // if someone cancels
}

