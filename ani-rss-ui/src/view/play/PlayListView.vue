<template>
  <PlayStartView ref="playStartRef"/>
  <el-dialog v-model="dialogVisible" :title="ani.title" center>
    <div v-loading="listLoading" v-if="list.length || listLoading">
      <el-scrollbar style="height: 500px;">
        <div class="grid-container">
          <div v-for="it in list">
            <el-card shadow="never">
              <div class="grid-item">
                <div>
                  <el-tooltip :content="it.title" placement="top">
                    <el-text :line-clamp="2">
                      {{ it.title }}
                    </el-text>
                  </el-tooltip>
                  <br/>
                  <el-text size="small" type="info">
                    {{ it.formatSize }}&nbsp;|&nbsp;{{ it.lastModifyFormat }}
                  </el-text>
                </div>
                <el-button circle
                           icon="VideoPlay"
                           size="large"
                           text
                           type="primary"
                           @click="playStartShow(it)"
                />
              </div>
            </el-card>
          </div>
        </div>
        <div class="bottom-spacer"></div>
      </el-scrollbar>
    </div>
    <div class="content" v-else>
      <el-text type="danger">
        未检测到已下载的剧集
      </el-text>
    </div>
    <div class="play-list-footer">
      <span class="total-text">共 {{ list.length }} 项</span>
      <el-button
          :loading="replenishLoading"
          bg
          icon="MagicStick"
          text
          type="warning"
          @click="replenish">
        补齐缺失集
      </el-button>
    </div>
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";
import {ElMessage} from "element-plus";
import PlayStartView from "./PlayStartView.vue";
import {fromNow} from "@/js/format.js";
import * as http from "@/js/http.js";

const dialogVisible = ref(false)
const listLoading = ref(false)
const replenishLoading = ref(false)
const list = ref([])

let ani = ref({})
let playStartRef = ref()

let playStartShow = (it) => {
  playStartRef.value?.show(JSON.parse(JSON.stringify(it)))
}

const refresh = () => {
  listLoading.value = true
  return http.playList(ani.value)
      .then(res => {
        list.value = res.data.map(it => {
          return {...it, lastModifyFormat: fromNow(it['lastModify'])}
        })
      })
      .finally(() => {
        listLoading.value = false
      })
}

const replenish = async () => {
  replenishLoading.value = true
  try {
    const res = await http.replenishMissingEpisodes(ani.value)
    ElMessage.success(res.message)
    await refresh()
  } finally {
    replenishLoading.value = false
  }
}

const show = (it) => {
  ani.value = it
  list.value = []
  dialogVisible.value = true
  refresh()
}

defineExpose({
  show
})
</script>


<style scoped>
.bottom-spacer {
  height: 5px;
}

.content {
  min-height: 200px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.grid-container {
  display: grid;
  grid-gap: 5px;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);
  padding: 0 5px;
}

.grid-item {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 4px;
}

.total-text {
  margin: 6px;
  text-align: end;
}

.play-list-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
