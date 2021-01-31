<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="版本">
          <a-input placeholder="请输入版本" v-decorator="['labelVersion', validatorRules.labelVersion ]" />
        </a-form-item>
         <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="描述">
                  <a-input placeholder="输入相关描述" v-decorator="['title',  validatorRules.title]" />
                </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="内容">
          <a-textarea  placeholder="请输入内容" v-decorator="['content', validatorRules.content ]" :auto-size="{ minRows: 5, maxRows: 20 }" />
        </a-form-item>
		    <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="状态" >
          <a-select placeholder="请选择状态" v-decorator="['enable', validatorRules.enable]">
            <a-select-option value='true'>启用</a-select-option>
            <a-select-option value='false'>未启用</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "LabelDrlModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        labelVersion:{rules: [{ required: true, message: '请输入版本号!' }]},
        content:{rules: [{ required: true, message: '请输入内容!' }]},
        title:{rules: [{ required: true, message: '请输入描述!' }]},
        enable:{rules: [{ required: true, message: '请输入启用状态!' }]},
        },
        url: {
          add: "/label/labelDrl/add",
          edit: "/label/labelDrl/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'labelVersion','content','title','enable'))
		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>