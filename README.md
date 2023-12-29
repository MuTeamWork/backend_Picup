
[![pihUemQ.png](https://s11.ax1x.com/2023/12/14/pihUemQ.png)](https://imgse.com/i/pihUemQ)
# PicUp

A lightweight, customizable image hosting system.


## Badges

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)


## Screenshots
[![pihUt0J.png](https://s11.ax1x.com/2023/12/14/pihUt0J.png)](https://imgse.com/i/pihUt0J)


[![pihUsXD.png](https://s11.ax1x.com/2023/12/14/pihUsXD.png)](https://imgse.com/i/pihUsXD)

## Architecture Diagram
[![architecture.png](https://www.freeimg.cn/i/2023/12/29/658e70658b9ae.png)](https://www.freeimg.cn/i/2023/12/29/658e70658b9ae.png)
## 🛠 Skills
Java, HTML, CSS, JavaScript,python...


## Run the project

### frontend

Clone the project

```bash
  git clone https://github.com/MuTeamWork/frontend_Pickup
```

Go to the project directory

```bash
  cd frontend_Pickup
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```

### backend

Clone the project

```bash
 git clone https://github.com/MuTeamWork/backend_Picup
```

Start the server
```bash
 script.sh
```

Build python image
```bash
docker build -t regulus/img_tagging:0.0.1 . 
```

Run dependencies
```bash
docker-compose up -d
```
## Tech Stack

**Client:** Vue, Vite, ElementPlus

**Server:** Java, SpringBoot, MybatisPlus,Python


## API Reference

#### User login 

```http
  GET /user/doLogin
```

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| body | body     | object | no       | none        |

#### Get user information

```http
  GET /user/getRole
```

| Name     | Type    | Required | Restrictions | Title | description |
| -------- | ------- | -------- | ------------ | ----- | ----------- |
| status | integer | true     | none         |       | none        |
| msg    | string  | true     | none         |       | none        |
| data   | string  | true     | none         |       | none        |

#### register

```http
  POST /user/register
```
| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| body | body     | object | no       | none        |


#### getFileList

```http
  GET /file/getFileList
```
| Name          | Type     | Required | Restrictions | Title | description |
| ------------- | -------- | -------- | ------------ | ----- | ----------- |
| status      | integer  | true     | none         |       | none        |
| msg         | string   | true     | none         |       | none        |
| data        | [object] | true     | none         |       | none        |
| fid        | integer  | true     | none         |       | none        |
| fileName   | string   | true     | none         |       | none        |
| file       | string   | true     | none         |       | none        |
| thumbnail  | string   | true     | none         |       | none        |
| expireTime | null     | true     | none         |       | none        |

## Future goals

- Light/dark mode toggle
- Live previews
- Fullscreen mode
- Cross platform
- OSS Object storage


## Authors

- [@lustresix](https://github.com/lustresix)

- [@Logthm](https://github.com/Logthm)

- [@aceukiyo](https://github.com/aceukiyo)

- [@Shuwugar](https://github.com/Shuwugar)

- [@teachermark](https://github.com/teachermark)

- [@whytakeaname](https://github.com/whytakeaname)

- [@Hathaway](https://github.com/Hathaway-Z)
## FAQ

#### How to upload an image?

You merely need to drag the image into the designated rectangular area on the upload page, or click on that area, and then select the desired image from the file picker that appears subsequently.

#### What is EXIF information? Should I preserve it?

EXIF data contains camera, date, location, and image details. Preserve it for photography analysis and organizing photos, but consider removing it for privacy and to reduce file size.

