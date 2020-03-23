package com.hjw.Bean.HYT;

import java.util.ArrayList;
import java.util.List;

public class Recommend {

	private List<RecommendOption> recommendOptions = new ArrayList<>();//推荐项目
	private RecommendPackage recommendPackage = new RecommendPackage();//推荐套餐
	
	public List<RecommendOption> getRecommendOptions() {
		return recommendOptions;
	}
	public void setRecommendOptions(List<RecommendOption> recommendOptions) {
		this.recommendOptions = recommendOptions;
	}
	public RecommendPackage getRecommendPackage() {
		return recommendPackage;
	}
	public void setRecommendPackage(RecommendPackage recommendPackage) {
		this.recommendPackage = recommendPackage;
	}
}
