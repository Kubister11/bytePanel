<template>
  <div class="w-[250px] h-full rounded-[10px] bg-black bg-opacity-30 drop-shadow-lg shadow-black flex flex-col items-center gap-6">
    <div class="w-full h-fit flex justify-center items-center flex-col mt-4 gap-2">

      <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
        <font-awesome-icon :icon="['fas', 'power-off']" class="text-[#ADC4FF] size-[20px] ml-1" />
        <div class="w-[1px] h-[18px] bg-white"></div>
        <p class="text-white bold-font tracking-wider text-[16px] ">Start</p>
      </div>


      <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
        <font-awesome-icon :icon="['fas', 'rotate']" class="text-[#ADC4FF] size-[20px] ml-1" />
        <div class="w-[1px] h-[18px] bg-white"></div>
        <p class="text-white bold-font tracking-wider text-[16px] ">Restart</p>
      </div>


      <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
        <font-awesome-icon :icon="['far', 'circle-stop']" class="text-[#ADC4FF] size-[20px] ml-1" />
        <div class="w-[1px] h-[18px] bg-white"></div>
        <p class="text-white bold-font tracking-wider text-[16px] ">Stop</p>
      </div>
    </div>

    <div class="w-[60%] h-[1px] bg-white opacity-30"></div>

    <div class="w-full h-fit flex justify-center items-center flex-col gap-2">


    </div>
  </div>
</template>
<script setup lang="ts">
</script>