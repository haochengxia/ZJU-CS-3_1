// mytest.cpp 
//
#include "FaceOp.h"

using namespace cv;
using namespace std;

int main(int argc, char** argv) {	
	if (argc <= 2) {
		cout << "usage: mytest.exe [testFaceImagePath] [modelPath] <faceSetDirPath> " << endl;
		cout << "Tips: <> representing optional para" << endl;
		return 0;
	}
	FaceModel facemodel;
	std::string dirPath("D://data");
	facemodel.load(dirPath);

	char* model_name = (char*)"default";
	char* file_name = (char*)"default";

	if (argc >= 3) {
		model_name = argv[2];
		file_name = argv[1];
	}
	if (argc >= 4) {
		// has dirPath
		dirPath = argv[3];
	}

	cout << "Step[1/3]: Get low dimension vector..." << endl;
	FileStorage model(model_name, FileStorage::READ |  FileStorage::BASE64);
	Mat e_vector_mat, e_value_mat;
	model["e_vector_mat"] >> e_vector_mat;
	model["e_value_mat"] >> e_value_mat;
	Mat distance;
	Mat samples;
	Face face;
	facemodel.samples.copyTo(samples);
	distance = e_vector_mat * samples;
	//cout << "Log: <after get distance>" << e_vector_mat.cols << endl;
	cout << "Step[2/3]: Compare NORM_L2 distance..." << endl;
	for (int _i = 1; _i <= facemodel.num_of_persons; ++_i) {
		for (int _j = 1; _j <= facemodel.faces_per_person_total; _j++)
		{
			string facePath("D://data/" + to_string(_i) + "/" + (_j < 10 ? '0' + std::to_string(_j) : std::to_string(_j)));
			//cout << "Log: facePath = " << facePath << endl;

			face.load(facePath, ".png");
			Mat face_vect = e_vector_mat * face.vect;
			double min_d = norm(face_vect, distance.col(0), NORM_L2);
			double temp_d = 0;
			int min_i = 0;
			//cout << "Log: distance.cols" << distance.cols <<endl;
			for (int i = 1; i < distance.cols; ++i) {
				temp_d = norm(face_vect, distance.col(i), NORM_L2);
				
				if (temp_d <= min_d) {
					min_d = temp_d;
					min_i = i;
				}
			}
			//cout << (min_i / facemodel.faces_per_person) + 1 << "/" << (min_i % facemodel.faces_per_person) + 1 << " ";
		}
		//cout << "Log: faceLoad complete" << endl;
		//cout << endl;
	}
	string fileNameStr(file_name);
	face.load(fileNameStr);
	//cout << "Log: fileNameStr = " << fileNameStr << endl;
	//cout << "Log: test pic loaded" << endl;
	Mat face_vect = e_vector_mat * face.vect;
	double min_d = norm(face_vect, distance.col(0), NORM_L2);
	double temp_d = 0;
	int min_i = 0;

	for (int i = 1; i < distance.cols; ++i) {
		temp_d = norm(face_vect, distance.col(i), NORM_L2);
		//cout << "i = " << i << "distance = " << temp_d << endl;
		if (temp_d <= min_d) {
			min_d = temp_d;
			min_i = i;
		}
	}
	cout << "min_i->" << min_i << endl;
	cout << (min_i / facemodel.faces_per_person) + 1 << "/" << (min_i % facemodel.faces_per_person + 1) << " " << endl;
	cout << "Step[3/3]: Mark and output..." << endl;
	Mat origin_mat = face.originFaceImg;
	Mat similar_mat = facemodel.faces.at(min_i)->originFaceImg;
	string text =   to_string(min_i / facemodel.faces_per_person + 1) + " -> " + to_string(min_i % facemodel.faces_per_person + 1);
	cout << text << endl;
	putText(origin_mat, text, Point(10, 95), CV_FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 2, 8);
	imshow("OriginFaceWithMarker", origin_mat);
	imshow("Similar Pic", similar_mat);
	waitKey(0);
	destroyAllWindows();
	return 0;
}