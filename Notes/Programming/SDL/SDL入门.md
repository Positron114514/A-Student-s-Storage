#程序设计 #图形库 #C #基础教程 #SDL 
# SDL 入门

依于[[../C/CPL进阶-翁恺|C语言]]的图形库


> Created By lxx11451 on 2024/1/8

> 2024/1/9 
>
> 草泥马昨天写的忘保存了 , 重敲吧
>
> 奈奈得



## 一 . 模板

**前 Init 后 destory 别忘了**

### 1. 显示文本

```c
//引入字体和字号
TTF_Font *font_title = TTF_OpenFont("./res/GenshinDefault.ttf",32);
//引入颜色
SDL_Color title_color = {0xff,0xff,0xff,0xff};
//创建surface和texture变量
SDL_Surface *surface_title = TTF_RenderText_Blended(font_title, a, title_color);
SDL_Texture *texture_title = SDL_CreateTextureFromSurface(shit, surface_title);
//用于形确定文字放置位置
SDL_Rect rect_title = {.x = 30, .y = 450};
SDL_QueryTexture(texture_title, NULL, NULL, &rect_title.w, &rect_title.h);
//加载到渲染器并显示
SDL_RenderCopy(shit, texture_title, NULL, &rect_title);
SDL_RenderPresent(shit);
```



### 2. 播放音乐

```c
//初始化打开参数
Mix_OpenAudio(MIX_DEFAULT_FREQUENCY,MIX_DEFAULT_FORMAT,MIX_DEFAULT_CHANNELS,2048);
//fx(可重复播放)
Mix_Chunk *sound_fx = Mix_LoadWAV("./res/bi.wav");
//music(不可重复播放)
Mix_Music *background_music = Mix_LoadMUS("./res/NoticeMe.mp3");
//播放music
Mix_PlayMusic(background_music,0);
//播放fx
Mix_PlayChunk(sound_fx,0);
```



### 3. 加载图片

```c
//载入图片surface & texture
SDL_Surface *surface_background = IMG_Load("./res/genshin.jpg");
SDL_Texture *texture_background = SDL_CreateTextureFromSurface(shit,surface_background);
//加载到render && 显示
SDL_RenderCopy(shit,texture_background,NULL,NULL);
SDL_RenderPresent(shit);
```



### 4. 渐入

- 文字

```c
while(color_title.a < 255) {
        //显示背景图片
        set_background_color(color_background);
        //显示标题
        display_text("./res/ttf/GenshinDefault.ttf", color_title, 72, title_coord);
        display();

        color_title.a += 3;
        SDL_Delay(32);
    }
```

- 音频

```c
//设置音量为0
    Mix_VolumeMusic(0);
    //播放音乐
    if(Mix_PlayMusic(launch_music,0) == -1){
        HANDLE_ERROR("Play Music");
    }

    int fade_in_step = 100;
    int fade_in_delay = 16;

    for (int i = 0; i <= fade_in_step; i++) {
        int volume = i * MIX_MAX_VOLUME / fade_in_step;
        Mix_VolumeMusic(volume);
        // 添加一点延时
        SDL_Delay(fade_in_delay);
    }
```





- 学习程序1

  ```c
  #include <stdio.h>
  #include <math.h>
  #include <string.h>
  
  #include "SDL2/SDL.h"
  #include "SDL2/SDL_image.h"
  #include "SDL2/SDL_ttf.h"
  #include "SDL2/SDL_mixer.h"
  
  #undef main
  
  char *num_to_str(unsigned int a){
      int x = (int)a;
      char *ret= (char *)malloc(sizeof(char)*3);
      if(a >= 10){
          ret[0] = x / 10 + '0';
          ret[1] = x % 10 + '0';
          ret[2] = '\0';
      }else{
          ret[0] = x + '0';
          ret[1] = '\0';
      }
      return ret;
  }
  
  int main(int argc, char* argv[]) {
      SDL_Init(SDL_INIT_VIDEO | SDL_INIT_TIMER);
      IMG_Init(IMG_INIT_JPG | IMG_INIT_PNG);
      TTF_Init();
      Mix_Init(MIX_INIT_MP3);
  
      SDL_Window *window = SDL_CreateWindow("Genshin",420,180,800,600,SDL_WINDOW_SHOWN);
      SDL_Renderer *shit = SDL_CreateRenderer(window,-1,SDL_RENDERER_ACCELERATED);
  
      Mix_OpenAudio(MIX_DEFAULT_FREQUENCY,MIX_DEFAULT_FORMAT,MIX_DEFAULT_CHANNELS,2048);
      Mix_Chunk *sound_fx = Mix_LoadWAV("./res/bi.wav");
      Mix_Music *background_music = Mix_LoadMUS("./res/NoticeMe.mp3");
  
      Mix_PlayMusic(background_music,0);
  
      unsigned int startup = SDL_GetTicks();
  
      SDL_Surface *surface_background = IMG_Load("./res/genshin.jpg");
      SDL_Texture *texture_background = SDL_CreateTextureFromSurface(shit,surface_background);
  
      SDL_RenderCopy(shit,texture_background,NULL,NULL);
      SDL_RenderPresent(shit);
  
      SDL_Delay(1200);
  
      TTF_Font *font_title = TTF_OpenFont("./res/GenshinDefault.ttf",32);
      SDL_Color title_color = {0xff,0xff,0xff,0xff};
  
      char a[100] = "You've enjoyed Notice Me for 0 seconds.";
  
      SDL_Surface *surface_title = TTF_RenderText_Blended(font_title, a, title_color);
      SDL_Texture *texture_title = SDL_CreateTextureFromSurface(shit, surface_title);
  
      SDL_Rect rect_title = {.x = 30, .y = 450};
  
  
      unsigned int time = SDL_GetTicks() - startup;
      while(time <= 60*1000) { 
          strcpy(a + 29, num_to_str(time/1000));
          strcat(a + 29 , " seconds.");
          surface_background = IMG_Load("./res/genshin.jpg");
          texture_background = SDL_CreateTextureFromSurface(shit,surface_background);
  
          SDL_RenderCopy(shit,texture_background,NULL,NULL);
  
          surface_title = TTF_RenderText_Blended(font_title, a, title_color);
          texture_title = SDL_CreateTextureFromSurface(shit, surface_title);
  
          SDL_QueryTexture(texture_title, NULL, NULL, &rect_title.w, &rect_title.h);
          SDL_RenderCopy(shit, texture_title, NULL, &rect_title);
          SDL_RenderPresent(shit);
          SDL_Delay(1000);
          time = SDL_GetTicks() - startup;
      }
  
      SDL_Color end_color = {0x00,0x00,0x00,0x00};
      SDL_Surface *surface_end = TTF_RenderText_Blended(font_title, "Task Finished! Now enjoy the Music!", end_color);
      SDL_Texture *texture_end = SDL_CreateTextureFromSurface(shit, surface_end);
  
      SDL_Rect rect_end = {.x = 40, .y = 490};
  
      SDL_QueryTexture(texture_end, NULL, NULL, &rect_end.w, &rect_end.h);
      SDL_RenderCopy(shit, texture_end, NULL, &rect_end);
      SDL_RenderPresent(shit);
      
      SDL_Delay(120000);
  
  
      Mix_CloseAudio();
      Mix_FreeChunk(sound_fx);
      Mix_FreeMusic(background_music);
  
      SDL_DestroyTexture(texture_background);
      SDL_FreeSurface(surface_background);
  
      SDL_DestroyTexture(surface_title);
      SDL_FreeSurface(surface_title);
      TTF_CloseFont(font_title);
  
      SDL_DestroyWindow(window);
      SDL_DestroyRenderer(shit);
  
      Mix_Quit();
      TTF_Quit();
      IMG_Quit();
      SDL_Quit();
      return 0;
  }
  
  ```

  