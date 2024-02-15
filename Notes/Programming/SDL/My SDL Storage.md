#SDL #Storage #C #程序设计 

我的[[SDL入门|SDL]]个人库

依于 2023-CPL-Project

# Display

## display.h

```c
// display.h
  
#ifndef FIN_PROGRAM_DISPLAY_H  
#define FIN_PROGRAM_DISPLAY_H  
  
#include <basic.h>  
//load text, must be destroyed after loaded  
extern TTF_Font *load_text(const char *path, int size);  
//load graph, must be destroyed after loaded  
extern SDL_Texture *load_graph(const char *path);  
//add the graph to the renderer, not displayed  
extern void display_graph(SDL_Texture *texture, Coord rect);  
//display text  
extern void display_text(TTF_Font *font, const char *content, SDL_Color color, Coord rect);  
//set background color, not displayed  
extern void set_background_color(SDL_Color color);  
//Display everything  
extern void display();  
//Destroy SDL_Texture *texture  
extern void destroy_graph(SDL_Texture *texture);  
//Destroy TTF_Font *font  
extern void destroy_ttf(TTF_Font *font);  
//open a file  
extern FILE *open_file(const char *path, const char *type);  
  
#endif //FIN_PROGRAM_DISPLAY_H
```

## display.c

```c
//  
// Created by Lenovo on 2024/1/9.  
//  
  
//单独写了display函数便于解决闪烁问题  
#include <display.h>  
  
extern TTF_Font *load_text(const char *path, int size){  
    TTF_Font *font = TTF_OpenFont(path,size);  
    if(font == NULL){  
        HANDLE_ERROR("Load Font");  
    }  
    return font;  
}  
  
extern SDL_Texture *load_graph(const char *path){  
    SDL_Surface *surface = IMG_Load(path);  
    if(surface == NULL){  
        HANDLE_ERROR("Load Image Surface");  
    }  
  
    SDL_Texture *texture = SDL_CreateTextureFromSurface(app.renderer,surface);  
    if(surface == NULL){  
        HANDLE_ERROR("Load Image Surface");  
    }  
    SDL_FreeSurface(surface);  
    return texture;  
}  
  
extern void destroy_graph(SDL_Texture *texture){  
    SDL_DestroyTexture(texture);  
}  
  
extern void destroy_ttf(TTF_Font *font){  
    TTF_CloseFont(font);  
}  
  
//add the text to the renderer, not displayed  
extern void display_text(TTF_Font *font, const char *content, SDL_Color color, Coord rect){  
    //创建surface和texture变量  
    SDL_Surface *surface = TTF_RenderText_Blended(font, content, color);  
    if(surface == NULL){  
        HANDLE_ERROR("Load Text Surface");  
    }  
    SDL_Texture *texture = SDL_CreateTextureFromSurface(app.renderer, surface);  
    if(texture == NULL){  
        HANDLE_ERROR("Load Text Texture");  
    }  
    //用于形确定文字放置位置  
    SDL_Rect rect_title = {.x = rect.x, .y = rect.y};  
    SDL_QueryTexture(texture, NULL, NULL, &rect_title.w, &rect_title.h);  
    //加载到渲染器并显示  
    SDL_RenderCopy(app.renderer, texture, NULL, &rect_title);  
  
    SDL_FreeSurface(surface);  
    SDL_DestroyTexture(texture);  
}  
  
//add the graph to the renderer, not displayed  
extern void display_graph(SDL_Texture *texture, Coord rect){  
    //载入图片surface & texture  
    if(rect.x == -1){  
        SDL_RenderCopy(app.renderer, texture, NULL, NULL);  
    }  
    else {  
        SDL_Rect rect_start = {.x = rect.x, .y = rect.y};  
        SDL_QueryTexture(texture, NULL, NULL, &rect_start.w, &rect_start.h);  
        //加载到renderer  
        SDL_RenderCopy(app.renderer, texture, NULL, &rect_start);  
    }  
}  
  
//set background color, not displayed  
extern void set_background_color(SDL_Color color){  
    SDL_SetRenderDrawColor(app.renderer, color.r, color.g, color.b, color.a);  
    SDL_RenderClear(app.renderer);  
}  
  
//Display everything  
extern void display(){  
    SDL_RenderPresent(app.renderer);  
}  
  
extern FILE *open_file(const char *path, const char *type){  
    FILE *file = fopen(path, type);  
    if(file == NULL){  
        HANDLE_ERROR("Open file");  
    }  
    return file;  
}
```

# Audio

## audio.h

```c
//  
// Created by Lenovo on 2024/1/9.  
//  
  
#ifndef FIN_PROGRAM_AUDIO_H  
#define FIN_PROGRAM_AUDIO_H  
  
#include <basic.h>  
  
//Init the audio. You should use function "quit_music" to release the memory  
extern void init_mix();  
//load music. must free after used  
extern Mix_Music *load_music(const char *path);  
// play music  
// You must use init_mix before using this function.  
extern void play_music(Mix_Music *music);  
//play music fade in  
extern void play_music_fade_in(Mix_Music *music, int fade_in_step, int fade_in_delay);  
//free Mix_music*  
extern void destroy_music(Mix_Music *music);  
//load fx. must free after used  
extern Mix_Chunk *load_fx(const char *path);  
// play fx  
// You must use init_mix before using this function.  
extern void play_fx(Mix_Chunk *sound, int channel);  
//free Mix_Chunk*  
extern void destroy_fx(Mix_Chunk *sound);  
  
//Quit the audio  
extern void quit_mix();  
  
#endif //FIN_PROGRAM_AUDIO_H
```

## audio.c

```c
//  
// Created by Lenovo on 2024/1/9.  
//  
#include <audio.h>  
//Init the audio. You should use function "quit_music" to release the memory  
extern void init_mix(){  
    //初始化打开参数  
    if(Mix_OpenAudio(MIX_DEFAULT_FREQUENCY,MIX_DEFAULT_FORMAT,MIX_DEFAULT_CHANNELS,2048)){  
        HANDLE_ERROR("Open Audio");  
    }  
}  
//Quit the audio  
extern void quit_mix(){  
    Mix_CloseAudio();  
}  
  
extern Mix_Music *load_music(const char *path){  
    Mix_Music *music = Mix_LoadMUS(path);  
    if (music == NULL) {  
        HANDLE_ERROR("Load music");  
    }  
    return music;  
}  
  
// play music  
// You must use init_mix before using this function.  
extern void play_music(Mix_Music *music){  
    if(Mix_PlayMusic(music,0) == -1){  
        HANDLE_ERROR("Play music");  
    }  
}  
  
extern void play_music_fade_in(Mix_Music * music, int fade_in_step, int fade_in_delay){  
    play_music(music);  
    for (int i = 0; i <= fade_in_step; i++) {  
        int volume = i * MIX_MAX_VOLUME / fade_in_step;  
        Mix_VolumeMusic(volume);  
        // 添加一点延时  
        SDL_Delay(fade_in_delay);  
    }  
}  
  
extern void destroy_music(Mix_Music *music){  
    Mix_FreeMusic(music);  
}  
  
extern Mix_Chunk *load_fx(const char *path){  
    Mix_Chunk* sound = Mix_LoadWAV(path);  
    if (sound == NULL) {  
        HANDLE_ERROR("Load FX");  
    }  
    return sound;  
}  
  
// play fx  
// You must use init_mix before using this function.  
extern void play_fx(Mix_Chunk *sound, int channel){  
    if(Mix_PlayChannel(channel, sound, 0) == -1){  
        HANDLE_ERROR("Play fx");  
    }  
}  
  
extern void destroy_fx(Mix_Chunk *sound){  
    Mix_FreeChunk(sound);  
}
```

# Color

## color.h

```c
//  
// Created by Lenovo on 2024/1/9.  
//  
  
//颜色们  
  
#ifndef FIN_PROGRAM_COLOR_H  
#define FIN_PROGRAM_COLOR_H  
  
#define WHITE {0xff, 0xff, 0xff, 0xff}  
#define BLACK {0x00, 0x00, 0x00, 0xff}  
#define RED   {0xff, 0x00, 0x00, 0xff}  
#define GREEN {0x00, 0xff, 0x00, 0xff}  
#define BLUE {0x00, 0x00, 0xff, 0xff}  
#define EVEN_LIGHT_GREEN {0xa0, 0xcc, 0xa0, 0xff}  
#define LIGHT_GREEN {0x90, 0xEE, 0x90, 0xff}  
#define PINK {0xff, 0xb6, 0xc1, 0xff}  
#define LIGHT_RED {0xff, 0x66, 0x66, 0xff}  
#define DARK_RED {0x8B, 0x00, 0x00, 0xff}  
#define DARK_PURPLE {0x80, 0x00, 0x80, 0xff}  
#define DARK_BLUE {0x00, 0x00, 0x80, 0xff}  
#define LIGHT_YELLOW {0xff, 0xd7, 0x00, 0xff}  
  
  
#endif //FIN_PROGRAM_COLOR_H
```


# Basic

## SDL_basic.h

```c
#ifndef FIN_PROGRAM_BASIC_H  
#define FIN_PROGRAM_BASIC_H  
  
#include <stdio.h>  
#include <stdlib.h>  
#include <stdbool.h>  
#include <time.h>  
#include <math.h>  
#include <time.h>  
#include <windows.h>  
  
#include "SDL2/SDL.h"  
#include "SDL2/SDL_main.h"  
#include "SDL2/SDL_events.h"  
#include "SDL2/SDL_mixer.h"  
#include "SDL2/SDL_ttf.h"  
#include "SDL2/SDL_image.h"  
  
#undef main  
  
//#define __MACRO__  
  
#ifdef __MACRO__  
#define log(...) printf(__VA_ARGS__)  
#else  
#define log(...)  
#endif  
  
#define WINDOW_WIDTH 1280  
#define WINDOW_HEIGHT 760  
  
#define HANDLE_ERROR(msg) log(msg ": %s\n",SDL_GetError()); \  
exit(EXIT_FAILURE)  
  
#define Max(a, b) (a) > (b) ? (a) : (b)  
#define Min(a, b) (a) > (b) ? (b) : (a)  
  
#define NAME "WHACK-A-MINE"  
  
#define LINE_SPACE 64  
  
#define GAME 0  
#define RECORDS 1  
#define RULES 2  
  
  
  
typedef struct{  
    SDL_Window *window;  
    SDL_Renderer *renderer;  
    bool *keyboard;  
    bool left_mouse;  
    bool right_mouse;  
}  App;  
  
typedef struct{  
    int x;  
    int y;  
} Coord;  
  
extern App app;  
  
#endif
```