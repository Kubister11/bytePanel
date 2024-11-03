<script setup lang="js">

import {useRoute} from "vue-router";
import {onMounted, reactive} from "vue";
import {sendPowerAction} from "@/fetcher/backend.js";

const route = useRoute();
const state = reactive({
  serverId: null
})

onMounted(() => {
  state.serverId = route.query.id
})

</script>

<template>
  <div class="w-[250px] h-full rounded-[10px] bg-black bg-opacity-20 drop-shadow-lg shadow-black flex flex-col items-center gap-6" data-aos="fade-right">
    <div class="w-full h-full flex flex-col items-center gap-6">
      <div class="w-full h-fit flex justify-center items-center flex-col mt-4 gap-2">

        <div @click="sendPowerAction(state.serverId, 'ON')" class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['fas', 'power-off']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Start</p>
        </div>


        <div @click="sendPowerAction(state.serverId, 'RESTART')" class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['fas', 'rotate']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Restart</p>
        </div>


        <div @click="sendPowerAction(state.serverId, 'OFF')" class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['far', 'circle-stop']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Stop</p>
        </div>
      </div>

      <div class="w-[60%] h-[1px] bg-white opacity-30"></div>

      <div class="w-full h-fit flex justify-center items-center flex-col gap-2">

        <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] bg-[#003F72] bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['fas', 'terminal']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Console</p>
        </div>

        <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['far', 'file']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Files</p>
        </div>

        <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['fas', 'retweet']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Backups</p>
        </div>

        <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['fas', 'gear']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Settings</p>
        </div>

        <div class="flex gap-4 items-center justify-start p-3 w-10/12 rounded-[10px] hover:bg-[#003F72] hover:bg-opacity-25 duration-200 cursor-pointer">
          <font-awesome-icon :icon="['fas', 'user']" class="text-[#ADC4FF] size-[20px] ml-1" />
          <div class="w-[1px] h-[18px] bg-white"></div>
          <p class="text-white bold-font tracking-wider text-[16px] ">Users</p>
        </div>

      </div>
    </div>

<!--    <div class="w-full h-[77px] flex flex-col items-center gap-6 bg-black rounded-b-[10px] bg-opacity-30">-->

<!--    </div>-->
  </div>
</template>