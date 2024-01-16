package com.roubsite.trans.utils;


import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FFmpegUtils {
	private static final Pattern FORMAT_PATTERN = Pattern.compile("^\\s*([D ])([E ])\\s+([\\w,]+)\\s+.+$");
	private static final Pattern ENCODER_DECODER_PATTERN = Pattern.compile("^\\s*([D ])([E ])([AVS]).{3}\\s+(.+)$", 2);
	private static final Pattern PROGRESS_INFO_PATTERN = Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\S+)\\s*", 2);
	private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+)", 2);
	private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("([\\d.]+)\\s+(?:fps|tb\\(r\\))", 2);
	private static final Pattern BIT_RATE_PATTERN = Pattern.compile("(\\d+)\\s+kb/s", 2);
	private static final Pattern SAMPLING_RATE_PATTERN = Pattern.compile("(\\d+)\\s+Hz", 2);
	private static final Pattern CHANNELS_PATTERN = Pattern.compile("(mono|stereo)", 2);
	private static final Pattern SUCCESS_PATTERN = Pattern.compile(
			"^\\s*video\\:\\S+\\s+audio\\:\\S+\\s+global " + "headers\\:\\S+.*$", 2);

	private FFMPEGLocator locator;

	public FFmpegUtils() {
		this.locator = new ZdyFFMPEGLocator();
		this.locator.createExecutor();
		Log.debug("ffmpeg:" + locator.getFFMPEGExecutablePath());
	}

	public FFmpegUtils(FFMPEGLocator locator) {
		this.locator = locator;
	}

	public String[] getAudioDecoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");

		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches()) {
							break;
						}

						String decoderFlag = matcher.group(1);
						String audioVideoFlag = matcher.group(3);
						if ("D".equals(decoderFlag) && "A".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
					} else if (line.trim().equals("Codecs:")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException var14) {
			throw new EncoderException(var14);
		} finally {
			ffmpeg.destroy();
		}

		int size = res.size();
		String[] ret = new String[size];

		for (int i = 0; i < size; ++i) {
			ret[i] = (String) res.get(i);
		}

		return ret;
	}

	public String[] getAudioEncoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");

		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches()) {
							break;
						}

						String encoderFlag = matcher.group(2);
						String audioVideoFlag = matcher.group(3);
						if ("E".equals(encoderFlag) && "A".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
					} else if (line.trim().equals("Codecs:")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException var14) {
			throw new EncoderException(var14);
		} finally {
			ffmpeg.destroy();
		}

		int size = res.size();
		String[] ret = new String[size];

		for (int i = 0; i < size; ++i) {
			ret[i] = (String) res.get(i);
		}

		return ret;
	}

	public String[] getVideoDecoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");

		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches()) {
							break;
						}

						String decoderFlag = matcher.group(1);
						String audioVideoFlag = matcher.group(3);
						if ("D".equals(decoderFlag) && "V".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
					} else if (line.trim().equals("Codecs:")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException var14) {
			throw new EncoderException(var14);
		} finally {
			ffmpeg.destroy();
		}

		int size = res.size();
		String[] ret = new String[size];

		for (int i = 0; i < size; ++i) {
			ret[i] = (String) res.get(i);
		}

		return ret;
	}

	public String[] getVideoEncoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");

		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches()) {
							break;
						}

						String encoderFlag = matcher.group(2);
						String audioVideoFlag = matcher.group(3);
						if ("E".equals(encoderFlag) && "V".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
					} else if (line.trim().equals("Codecs:")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException var14) {
			throw new EncoderException(var14);
		} finally {
			ffmpeg.destroy();
		}

		int size = res.size();
		String[] ret = new String[size];

		for (int i = 0; i < size; ++i) {
			ret[i] = (String) res.get(i);
		}

		return ret;
	}

	public String[] getSupportedEncodingFormats() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");

		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = FORMAT_PATTERN.matcher(line);
						if (!matcher.matches()) {
							break;
						}

						String encoderFlag = matcher.group(2);
						if ("E".equals(encoderFlag)) {
							String aux = matcher.group(3);
							StringTokenizer st = new StringTokenizer(aux, ",");

							while (st.hasMoreTokens()) {
								String token = st.nextToken().trim();
								if (!res.contains(token)) {
									res.add(token);
								}
							}
						}
					} else if (line.trim().equals("File formats:")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException var15) {
			throw new EncoderException(var15);
		} finally {
			ffmpeg.destroy();
		}

		int size = res.size();
		String[] ret = new String[size];

		for (int i = 0; i < size; ++i) {
			ret[i] = (String) res.get(i);
		}

		return ret;
	}

	public String[] getSupportedDecodingFormats() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");

		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = FORMAT_PATTERN.matcher(line);
						if (!matcher.matches()) {
							break;
						}

						String decoderFlag = matcher.group(1);
						if ("D".equals(decoderFlag)) {
							String aux = matcher.group(3);
							StringTokenizer st = new StringTokenizer(aux, ",");

							while (st.hasMoreTokens()) {
								String token = st.nextToken().trim();
								if (!res.contains(token)) {
									res.add(token);
								}
							}
						}
					} else if (line.trim().equals("File formats:")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException var15) {
			throw new EncoderException(var15);
		} finally {
			ffmpeg.destroy();
		}

		int size = res.size();
		String[] ret = new String[size];

		for (int i = 0; i < size; ++i) {
			ret[i] = (String) res.get(i);
		}

		return ret;
	}

	public MultimediaInfo getInfo(String source) throws InputFormatException, EncoderException {
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-i");
		ffmpeg.addArgument(source);

		try {
			ffmpeg.execute();
		} catch (IOException var10) {
			throw new EncoderException(var10);
		}

		MultimediaInfo var6;
		try {
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getErrorStream()));
			var6 = this.parseMultimediaInfo(source, reader);
		} finally {
			ffmpeg.destroy();
		}

		return var6;
	}

	private MultimediaInfo parseMultimediaInfo(String source, RBufferedReader reader) throws EncoderException {
		Pattern p1 = Pattern.compile("^\\s*Input #0, (\\w+).+$\\s*", 2);
		Pattern p2 = Pattern.compile("^\\s*Duration: (\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d).*$", 2);
		Pattern p3 = Pattern.compile("^\\s*Stream #\\S+: ((?:Audio)|(?:Video)|(?:Data)): (.*)\\s*$", 2);
		MultimediaInfo info = null;

		try {
			int step = 0;

			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}

				String specs;
				String type;
				if (step == 0) {
					String token = source + ": ";
					if (line.startsWith(token)) {
						type = line.substring(token.length());
						throw new InputFormatException(type);
					}

					Matcher m = p1.matcher(line);
					if (m.matches()) {
						specs = m.group(1);
						info = new MultimediaInfo();
						info.setFormat(specs);
						++step;
					}
				} else {
					Matcher m;
					if (step == 1) {
						m = p2.matcher(line);
						if (m.matches()) {
							long hours = (long) Integer.parseInt(m.group(1));
							long minutes = (long) Integer.parseInt(m.group(2));
							long seconds = (long) Integer.parseInt(m.group(3));
							long dec = (long) Integer.parseInt(m.group(4));
							long duration =
									dec * 100L + seconds * 1000L + minutes * 60L * 1000L + hours * 60L * 60L * 1000L;
							info.setDuration(duration);
							++step;
						} else {
							step = 3;
						}
					} else if (step == 2) {
						m = p3.matcher(line);
						if (m.matches()) {
							type = m.group(1);
							specs = m.group(2);
							StringTokenizer st;
							String token;
							Matcher m2;
							int i;
							boolean parsed;
							int bitRate;
							if ("Video".equalsIgnoreCase(type)) {
								VideoInfo video = new VideoInfo();
								st = new StringTokenizer(specs, ",");

								for (i = 0; st.hasMoreTokens(); ++i) {
									token = st.nextToken().trim();
									if (i == 0) {
										video.setDecoder(token);
									} else {
										parsed = false;
										m2 = SIZE_PATTERN.matcher(token);
										if (!parsed && m2.find()) {
											bitRate = Integer.parseInt(m2.group(1));
											int height = Integer.parseInt(m2.group(2));
											video.setSize(new VideoSize(bitRate, height));
											parsed = true;
										}

										m2 = FRAME_RATE_PATTERN.matcher(token);
										if (!parsed && m2.find()) {
											try {
												float frameRate = Float.parseFloat(m2.group(1));
												video.setFrameRate(frameRate);
											} catch (NumberFormatException var20) {
											}

											parsed = true;
										}

										m2 = BIT_RATE_PATTERN.matcher(token);
										if (!parsed && m2.find()) {
											bitRate = Integer.parseInt(m2.group(1));
											video.setBitRate(bitRate);
											parsed = true;
										}
									}
								}

								info.setVideo(video);
							} else if ("Audio".equalsIgnoreCase(type)) {
								AudioInfo audio = new AudioInfo();
								st = new StringTokenizer(specs, ",");

								for (i = 0; st.hasMoreTokens(); ++i) {
									token = st.nextToken().trim();
									if (i == 0) {
										audio.setDecoder(token);
									} else {
										parsed = false;
										m2 = SAMPLING_RATE_PATTERN.matcher(token);
										if (!parsed && m2.find()) {
											bitRate = Integer.parseInt(m2.group(1));
											audio.setSamplingRate(bitRate);
											parsed = true;
										}

										m2 = CHANNELS_PATTERN.matcher(token);
										if (!parsed && m2.find()) {
											String ms = m2.group(1);
											if ("mono".equalsIgnoreCase(ms)) {
												audio.setChannels(1);
											} else if ("stereo".equalsIgnoreCase(ms)) {
												audio.setChannels(2);
											}

											parsed = true;
										}

										m2 = BIT_RATE_PATTERN.matcher(token);
										if (!parsed && m2.find()) {
											bitRate = Integer.parseInt(m2.group(1));
											audio.setBitRate(bitRate);
											parsed = true;
										}
									}
								}

								info.setAudio(audio);
							}
						} else {
							step = 3;
						}
					}
				}

				if (step == 3) {
					reader.reinsertLine(line);
					break;
				}
			}
		} catch (IOException var21) {
			throw new EncoderException(var21);
		}

		if (info == null) {
			throw new InputFormatException();
		} else {
			return info;
		}
	}

	private Hashtable parseProgressInfoLine(String line) {
		Hashtable table = null;
		Matcher m = PROGRESS_INFO_PATTERN.matcher(line);

		while (m.find()) {
			if (table == null) {
				table = new Hashtable();
			}

			String key = m.group(1);
			String value = m.group(2);
			table.put(key, value);
		}

		return table;
	}

	public static void getThumb(String videoFilename, String thumbFilename, int hour, int min, float sec) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(new ZdyFFMPEGLocator().getFFMPEGExecutablePath(), "-y", "-i", videoFilename, "-vframes", "1", "-ss",
				hour + ":" + min + ":" + sec, "-f", "mjpeg", "-an", thumbFilename);
		Process process = processBuilder.start();
		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) ;
		process.waitFor();
		if (br != null) br.close();
		if (isr != null) isr.close();
		if (stderr != null) stderr.close();
	}

	public void encode(String source, File target) throws Exception {
		this.encode(source, target, null);
	}

	public void encode(String source, File target, EncoderProgressListener listener) throws Exception {
		target = target.getAbsoluteFile();
		target.getParentFile().mkdirs();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-protocol_whitelist");
		ffmpeg.addArgument("concat,file,http,https,tcp,tls,crypto");
		ffmpeg.addArgument("-i");
		ffmpeg.addArgument(source);

		ffmpeg.addArgument("-c:v");
		ffmpeg.addArgument("copy");
		ffmpeg.addArgument("-c:a");
		ffmpeg.addArgument("copy");

		ffmpeg.addArgument(target.getAbsolutePath());
		ffmpeg.addArgument("-y");
		System.out.println(ffmpeg.toString());
		ffmpeg.execute();

		try {
			long progress;
			RBufferedReader reader;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getErrorStream()));
			MultimediaInfo info = this.parseMultimediaInfo(source, reader);
			long duration = info.getDuration();

			if (listener != null) {
				listener.sourceInfo(info);
			}

			int step = 0;

			String line;
			while ((line = reader.readLine()) != null) {
				if (step == 0) {
					if (line.startsWith("WARNING: ")) {
						if (listener != null) {
							listener.message(line);
						}
					} else {
						++step;
					}
				} else if (step == 1 && !line.startsWith("  ")) {
					++step;
				}

				if (step == 2) {
					++step;
				} else if (step == 3 && !line.startsWith("  ")) {
					++step;
				}

				if (step == 4) {
					line = line.trim();
					if (line.length() > 0) {
						Hashtable table = this.parseProgressInfoLine(line);
						if (table == null) {
							if (listener != null) {
								listener.message(line);
							}

						} else {
							if (listener != null) {
								String time = (String) table.get("time");
								if (time != null) {
									int dot = time.indexOf(46);
									if (dot > 0 && dot == time.length() - 2 && duration > 0L) {
										String p1 = time.substring(0, dot);
										String p2 = time.substring(dot + 1);

										try {
											long i1 = Long.parseLong(p1);
											long i2 = Long.parseLong(p2);
											progress = i1 * 1000L + i2 * 100L;
											int perm = (int) Math.round(
													(double) (progress * 1000L) / (double) duration);
											if (perm > 1000) {
												perm = 1000;
											}

											listener.progress(perm);
										} catch (NumberFormatException var36) {
										}
									}
								}
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ffmpeg.destroy();
		}
	}
}
