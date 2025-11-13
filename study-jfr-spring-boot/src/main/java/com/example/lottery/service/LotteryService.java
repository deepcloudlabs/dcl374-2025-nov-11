package com.example.lottery.service;

import com.example.lottery.model.LotteryModel;

public interface LotteryService {

	LotteryModel draw(int max, int size);

}
