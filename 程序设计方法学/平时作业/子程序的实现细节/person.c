// file: person.c
#include<stdio.h>
struct person{
    int age;
    char name[8];
    int male;   // 0 - > not male & 1 -> male  
    int female;	// 0 - > not female & 1 -> female
    int height;
    int weight;
};

struct person create(int sex) __attribute__((noinline));

int main(){
    int sex0 = 0;
    int sex1 = 1;
    struct person worker0, worker1;
    worker0 = create(sex0);
    worker1 = create(sex1);
    printf("%d",worker0.age);
    printf("%d",worker1.age);
    return 0;
}
struct person create(int sex){
    struct person worker;
    if (sex == 0){
        struct person male;
        // check whehter the same name vari will use the same space 
        int sameName = 0;
        int sameNameV = 0;
        int diffName0 = 0;
        male.age = 20;
        male.female = 0;
        male.male = 1;
        male.name[0] = 'T';
        male.height = 180;
        male.weight = 120;
        worker = male;
    }
    else if (sex == 1){
        // check whehter the same name vari will use the same space 
        struct person female;
        int sameName = 1;
        int sameNameV = 0;
        int diffName1 = 1;
        female.age = 20;
        female.female = 1;
        female.male = 0;
        female.name[0] = 'L';
        female.height = 170;
        female.weight = 100;
        worker = female;
    }
    return worker;
}
