#include "FaceOp.h"
using namespace cv;
using namespace std;

Mat toImg(Mat vect, int w, int h);
	/** Arg List:
	*	energyPercent	argv[1]
	*	modelFileName	argv[2]
	*/
int main(int argc, char** argv) {
	const char* model_name = (char*)"default";
	double energy = 0.95;
	if (argc <= 2) {
		std::cout << "usage: mytrain.exe [energyPercent] [modelFileName] <faceSetDirPath> " << std::endl;
		std::cout << "<> representing optional para" << endl;
		return 0;
	}
	else if (argc >= 3) {
		model_name = argv[2];
		energy = atof(argv[1]);
	}
	std::string dirPath("D://data");
	if (argc >= 4) {
		dirPath = argv[3];
	}
	FaceModel facemodel;
	
	facemodel.load(dirPath);
	Mat samples, cov_mat, mean_mat;
	facemodel.samples.copyTo(samples);
	//cout << samples << endl;
	//cout << samples.size() << endl;
	cout << "Step[1/4]: Calculating Covariance Mat..." << endl;
	calcCovarMatrix(samples, cov_mat, mean_mat, CV_COVAR_ROWS | CV_COVAR_NORMAL);
	cout << mean_mat.size() << endl;
	//cout << cov_mat << endl;
	//cout << cov_mat.size() << endl;
	cov_mat = cov_mat / (samples.rows - 1);
	Mat e_vector_mat, e_value_mat;
	cout << "Step[2/4]: Calculating Eigen Vector..." << endl;
	eigen(cov_mat, e_value_mat, e_vector_mat);
	//cout << "eigen size " << e_value_mat.size() << endl;
	//cout << e_value_mat << endl;
	for (int i = 0; i < samples.rows; ++i) {
		samples.row(i) -= mean_mat;
	}

	double value_sum = sum(e_value_mat)[0];
	//cout << e_vector_mat.size() << endl;
	double energy_level = value_sum * energy;
	double energy_sum = 0;
	int k = 0;
	//cout << "Log: <before energy_sum> e_value_mat.rows = " << e_value_mat.rows << endl;
	for (k = 0; k < e_value_mat.rows; k++)
	{
		energy_sum += e_value_mat.at<double>(k, 0);
		if (energy_sum >= energy_level) break;
	}
	//cout << k << endl;
	e_vector_mat = (samples * e_vector_mat.t()).t();
	e_vector_mat = e_vector_mat.rowRange(0, k);
	e_value_mat = e_value_mat.rowRange(0, k);
	cout << "Step[3/4]: Save model..." << endl;
	// save model data in binary format 
	FileStorage model(model_name, FileStorage::WRITE |FileStorage::BASE64);
	model << "e_vector_mat" << e_vector_mat;
	model << "e_value_mat" << e_value_mat;
	model.release();

	cout << "Step[4/4]:Output Top Ten EigenFace..." << endl;
	//cout<<"Log: <before toImg> e_value_mat.rows = "<< e_value_mat.rows<<endl;
	vector<Mat> Top10EigenFace;
	for (int i = 0; i < 10; ++i) {
		Top10EigenFace.push_back(toImg(e_vector_mat.row(i), WIDTH, HEIGHT));
	}
	Mat result;
	hconcat(Top10EigenFace, result);
	result.convertTo(result, CV_8U, 255);
	imshow("Top Ten EigenFace", result);
	imwrite("Top Ten EigenFace.png", result);

	waitKey(0);
	destroyAllWindows();
	return 0;
}


Mat toImg(Mat vect, int w, int h) {
	assert(vect.type() == 6);
	//cout << vect.cols << endl;
	assert(vect.cols == w * h);
	Mat result(Size(w, h), CV_64FC1);
	for (int i = 0; i < h; ++i) {
		vect.colRange(i * w, (i + 1) * w).convertTo(result.row(i), CV_64FC1);
	}
	//cout << "success" << endl;
	normalize(result, result, 1.0, 0.0, NORM_MINMAX);
	return result;
}