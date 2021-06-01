#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

int main() {
    srand(time(NULL));
    int fd = open("fisier.txt", O_RDWR | O_CREAT | O_TRUNC, 0777);
    if (fd == -1) {
        perror("Failed to open file");
        return 1;
    }
    while (1) {
        int n = (rand() % 10 + 40);
        char *toBeWritten = (char *)malloc(2 * sizeof(char));
        sprintf(toBeWritten, "%d", n);
        fprintf(stderr, "%s ", toBeWritten);
        if (write(fd, toBeWritten, 2 * sizeof(char)) == -1) {
            perror("Failed to write in file");
            return 2;
        }
        lseek(fd, 0, SEEK_SET);
        sleep(1);
        free(toBeWritten);
    }

    return 0;
}