package com.roubsite.trans.utils;


import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encoder {
	private static final Log _log = new Log();
	private static final Pattern FORMAT_PATTERN = Pattern.compile("^\\s*([D ])([E ])\\s+([\\w,]+)\\s+.+$");

	private static final Pattern ENCODER_DECODER_PATTERN = Pattern.compile("^\\s*([D ])([E ])([AVS]).{3}\\s+(.+)$", 2);

	private static final Pattern PROGRESS_INFO_PATTERN = Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\S+)\\s*", 2);

	private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+)", 2);

	private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("([\\d.]+)\\s+(?:fps|tbr)", 2);

	private static final Pattern BIT_RATE_PATTERN = Pattern.compile("(\\d+)\\s+kb/s", 2);

	private static final Pattern SAMPLING_RATE_PATTERN = Pattern.compile("(\\d+)\\s+Hz", 2);

	private static final Pattern CHANNELS_PATTERN = Pattern.compile("(mono|stereo)", 2);

	private static final Pattern SUCCESS_PATTERN = Pattern.compile("^\\s*video\\:\\S+\\s+audio\\:\\S+\\s+subtitle\\:\\S+\\s+global headers\\:\\S+.*$", 2);

	private FFMPEGLocator locator;

	public Encoder() {
		this.locator = new ZdyFFMPEGLocator();
	}

	public Encoder(FFMPEGLocator locator) {
		this.locator = locator;
	}

	public String[] getAudioDecoders() throws EncoderException {
		ArrayList<String> res = new ArrayList<String>();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) continue;
				if (evaluate) {
					Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
					if (matcher.matches()) {
						String decoderFlag = matcher.group(1);
						String audioVideoFlag = matcher.group(3);
						if ("D".equals(decoderFlag) && "A".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
						continue;
					}
					break;
				}
				if (line.trim().equals("Codecs:")) evaluate = true;
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++)
			ret[i] = res.get(i);
		return ret;
	}

	public String[] getAudioEncoders() throws EncoderException {
		ArrayList<String> res = new ArrayList<String>();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) continue;
				if (evaluate) {
					Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
					if (matcher.matches()) {
						String encoderFlag = matcher.group(2);
						String audioVideoFlag = matcher.group(3);
						if ("E".equals(encoderFlag) && "A".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
						continue;
					}
					break;
				}
				if (line.trim().equals("Codecs:")) evaluate = true;
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++)
			ret[i] = res.get(i);
		return ret;
	}

	public String[] getVideoDecoders() throws EncoderException {
		ArrayList<String> res = new ArrayList<String>();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) continue;
				if (evaluate) {
					Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
					if (matcher.matches()) {
						String decoderFlag = matcher.group(1);
						String audioVideoFlag = matcher.group(3);
						if ("D".equals(decoderFlag) && "V".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
						continue;
					}
					break;
				}
				if (line.trim().equals("Codecs:")) evaluate = true;
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++)
			ret[i] = res.get(i);
		return ret;
	}

	public String[] getVideoEncoders() throws EncoderException {
		ArrayList<String> res = new ArrayList<String>();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) continue;
				if (evaluate) {
					Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
					if (matcher.matches()) {
						String encoderFlag = matcher.group(2);
						String audioVideoFlag = matcher.group(3);
						if ("E".equals(encoderFlag) && "V".equals(audioVideoFlag)) {
							String name = matcher.group(4);
							res.add(name);
						}
						continue;
					}
					break;
				}
				if (line.trim().equals("Codecs:")) evaluate = true;
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++)
			ret[i] = res.get(i);
		return ret;
	}

	public String[] getSupportedEncodingFormats() throws EncoderException {
		ArrayList<String> res = new ArrayList<String>();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) continue;
				if (evaluate) {
					Matcher matcher = FORMAT_PATTERN.matcher(line);
					if (matcher.matches()) {
						String encoderFlag = matcher.group(2);
						if ("E".equals(encoderFlag)) {
							String aux = matcher.group(3);
							StringTokenizer st = new StringTokenizer(aux, ",");
							while (st.hasMoreTokens()) {
								String token = st.nextToken().trim();
								if (!res.contains(token)) res.add(token);
							}
						}
						continue;
					}
					break;
				}
				if (line.trim().equals("File formats:")) evaluate = true;
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++)
			ret[i] = res.get(i);
		return ret;
	}

	public String[] getSupportedDecodingFormats() throws EncoderException {
		ArrayList<String> res = new ArrayList<String>();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));
			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) continue;
				if (evaluate) {
					Matcher matcher = FORMAT_PATTERN.matcher(line);
					if (matcher.matches()) {
						String decoderFlag = matcher.group(1);
						if ("D".equals(decoderFlag)) {
							String aux = matcher.group(3);
							StringTokenizer st = new StringTokenizer(aux, ",");
							while (st.hasMoreTokens()) {
								String token = st.nextToken().trim();
								if (!res.contains(token)) res.add(token);
							}
						}
						continue;
					}
					break;
				}
				if (line.trim().equals("File formats:")) evaluate = true;
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++)
			ret[i] = res.get(i);
		return ret;
	}

	public MultimediaInfo getInfo(File source) throws InputFormatException, EncoderException {
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-i");
		ffmpeg.addArgument(source.getAbsolutePath());
		try {
			ffmpeg.execute();
		} catch (IOException e) {
			throw new EncoderException(e);
		}
		try {
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getErrorStream()));
			return parseMultimediaInfo(source, reader);
		} finally {
			ffmpeg.destroy();
		}
	}

	private MultimediaInfo parseMultimediaInfo(File source, RBufferedReader reader) throws InputFormatException, EncoderException {
		Pattern p1 = Pattern.compile("^\\s*Input #0, (\\w+).+$\\s*", 2);
		Pattern p2 = Pattern.compile("^\\s*Duration: (\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d\\d).*$", 2);
		Pattern p3 = Pattern.compile("^\\s*Stream #\\S+: ((?:Audio)|(?:Video)|(?:Data)): (.*)\\s*$", 2);
		Pattern p4 = Pattern.compile("^\\s*Metadata:", 2);
		MultimediaInfo info = null;
		try {
			int step = 0;
			while (true) {
				String line = reader.readLine();
				_log.warn("Output line: " + line);
				if (line == null) break;
				if (step == 0) {
					String token = source.getAbsolutePath() + ": ";
					if (line.startsWith(token)) {
						String message = line.substring(token.length());
						throw new InputFormatException(message);
					}
					Matcher m = p1.matcher(line);
					if (m.matches()) {
						String format = m.group(1);
						info = new MultimediaInfo();
						info.setFormat(format);
						step++;
					}
				} else if (step == 1) {
					Matcher m = p2.matcher(line);
					if (m.matches()) {
						long hours = Integer.parseInt(m.group(1));
						long minutes = Integer.parseInt(m.group(2));
						long seconds = Integer.parseInt(m.group(3));
						long dec = Integer.parseInt(m.group(4));
						long duration = dec * 10L + seconds * 1000L + minutes * 60L * 1000L + hours * 60L * 60L * 1000L;
						info.setDuration(duration);
						step++;
					}
				} else if (step == 2) {
					Matcher m = p3.matcher(line);
					Matcher m4 = p4.matcher(line);
					if (m.matches()) {
						String type = m.group(1);
						String specs = m.group(2);
						if ("Video".equalsIgnoreCase(type)) {
							VideoInfo video = new VideoInfo();
							StringTokenizer st = new StringTokenizer(specs, ",");
							for (int i = 0; st.hasMoreTokens(); i++) {
								String token = st.nextToken().trim();
								if (i == 0) {
									video.setDecoder(token);
								} else {
									boolean parsed = false;
									Matcher m2 = SIZE_PATTERN.matcher(token);
									if (!parsed && m2.find()) {
										int width = Integer.parseInt(m2.group(1));
										int height = Integer.parseInt(m2.group(2));
										video.setSize(new VideoSize(width, height));
										parsed = true;
									}
									m2 = FRAME_RATE_PATTERN.matcher(token);
									if (!parsed && m2.find()) {
										try {
											float frameRate = Float.parseFloat(m2.group(1));
											video.setFrameRate(frameRate);
										} catch (NumberFormatException e) {
											_log.info("Invalid frame rate value: " + m2.group(1), e);
										}
										parsed = true;
									}
									m2 = BIT_RATE_PATTERN.matcher(token);
									if (!parsed && m2.find()) {
										int bitRate = Integer.parseInt(m2.group(1));
										video.setBitRate(bitRate);
										parsed = true;
									}
								}
							}
							info.setVideo(video);
						} else if ("Audio".equalsIgnoreCase(type)) {
							AudioInfo audio = new AudioInfo();
							StringTokenizer st = new StringTokenizer(specs, ",");
							for (int i = 0; st.hasMoreTokens(); i++) {
								String token = st.nextToken().trim();
								if (i == 0) {
									audio.setDecoder(token);
								} else {
									boolean parsed = false;
									Matcher m2 = SAMPLING_RATE_PATTERN.matcher(token);
									if (!parsed && m2.find()) {
										int samplingRate = Integer.parseInt(m2.group(1));
										audio.setSamplingRate(samplingRate);
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
										int bitRate = Integer.parseInt(m2.group(1));
										audio.setBitRate(bitRate);
										parsed = true;
									}
								}
							}
							info.setAudio(audio);
						}
					}
				}
				if (line.startsWith("frame=")) {
					reader.reinsertLine(line);
					break;
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		}
		if (info == null) throw new InputFormatException();
		return info;
	}

	private HashMap<String, String> parseProgressInfoLine(String line) {
		HashMap<String, String> table = null;
		Matcher m = PROGRESS_INFO_PATTERN.matcher(line);
		while (m.find()) {
			if (table == null) table = new HashMap<String, String>();
			String key = m.group(1);
			String value = m.group(2);
			table.put(key, value);
		}
		return table;
	}

	public void encode(File source, File target, EncodingAttributes attributes) throws IllegalArgumentException, InputFormatException, EncoderException {
		encode(source, target, attributes, null);
	}

	public void encode(File source, File target, EncodingAttributes attributes, EncoderProgressListener listener) throws IllegalArgumentException, InputFormatException, EncoderException {
		String formatAttribute = attributes.getFormat();
		Float offsetAttribute = attributes.getOffset();
		Float durationAttribute = attributes.getDuration();
		AudioAttributes audioAttributes = attributes.getAudioAttributes();
		VideoAttributes videoAttributes = attributes.getVideoAttributes();
		if (audioAttributes == null && videoAttributes == null)
			throw new IllegalArgumentException("Both audio and video attributes are null");
		target = target.getAbsoluteFile();
		target.getParentFile().mkdirs();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		if (offsetAttribute != null) {
			ffmpeg.addArgument("-ss");
			ffmpeg.addArgument(String.valueOf(offsetAttribute.floatValue()));
		}
		ffmpeg.addArgument("-i");
		ffmpeg.addArgument(source.getAbsolutePath());
		if (durationAttribute != null) {
			ffmpeg.addArgument("-t");
			ffmpeg.addArgument(String.valueOf(durationAttribute.floatValue()));
		}
		if (videoAttributes == null) {
			ffmpeg.addArgument("-vn");
		} else {
			String codec = videoAttributes.getCodec();
			if (codec != null) {
				ffmpeg.addArgument("-vcodec");
				ffmpeg.addArgument(codec);
			}
			String tag = videoAttributes.getTag();
			if (tag != null) {
				ffmpeg.addArgument("-vtag");
				ffmpeg.addArgument(tag);
			}
			Integer bitRate = videoAttributes.getBitRate();
			if (bitRate != null) {
				ffmpeg.addArgument("-vb");
				ffmpeg.addArgument(String.valueOf(bitRate.intValue()));
			}
			Integer frameRate = videoAttributes.getFrameRate();
			if (frameRate != null) {
				ffmpeg.addArgument("-r");
				ffmpeg.addArgument(String.valueOf(frameRate.intValue()));
			}
			VideoSize size = videoAttributes.getSize();
			if (size != null) {
				ffmpeg.addArgument("-s");
				ffmpeg.addArgument(String.valueOf(size.getWidth()) + "x" + String.valueOf(size.getHeight()));
			}
			if (videoAttributes.isFaststart()) {
				ffmpeg.addArgument("-movflags");
				ffmpeg.addArgument("faststart");
			}
			if (videoAttributes.getX264Profile() != null) {
				ffmpeg.addArgument("-profile:v");
				ffmpeg.addArgument(videoAttributes.getX264Profile().getModeName());
			}
		}
		if (audioAttributes == null) {
			ffmpeg.addArgument("-an");
		} else {
			String codec = audioAttributes.getCodec();
			if (codec != null) {
				if (codec.equals("aac")) codec = "libvo_aacenc";
				ffmpeg.addArgument("-acodec");
				ffmpeg.addArgument(codec);
			}
			Integer bitRate = audioAttributes.getBitRate();
			if (bitRate != null) {
				ffmpeg.addArgument("-ab");
				ffmpeg.addArgument(String.valueOf(bitRate.intValue()));
			}
			Integer channels = audioAttributes.getChannels();
			if (channels != null) {
				ffmpeg.addArgument("-ac");
				ffmpeg.addArgument(String.valueOf(channels.intValue()));
			}
			Integer samplingRate = audioAttributes.getSamplingRate();
			if (samplingRate != null) {
				ffmpeg.addArgument("-ar");
				ffmpeg.addArgument(String.valueOf(samplingRate.intValue()));
			}
			Integer volume = audioAttributes.getVolume();
			if (volume != null) {
				ffmpeg.addArgument("-vol");
				ffmpeg.addArgument(String.valueOf(volume.intValue()));
			}
		}
		ffmpeg.addArgument("-f");
		ffmpeg.addArgument(formatAttribute);
		ffmpeg.addArgument("-y");
		ffmpeg.addArgument(target.getAbsolutePath());
		try {
			ffmpeg.execute();
		} catch (IOException e) {
			throw new EncoderException(e);
		}
		try {
			long duration;
			String lastWarning = null;
			long progress = 0L;
			RBufferedReader reader = new RBufferedReader(new InputStreamReader(ffmpeg.getErrorStream()));
			MultimediaInfo info = parseMultimediaInfo(source, reader);
			if (durationAttribute != null) {
				duration = Math.round(durationAttribute.floatValue() * 1000.0F);
			} else {
				duration = info.getDuration();
				if (offsetAttribute != null) duration -= Math.round(offsetAttribute.floatValue() * 1000.0F);
			}
			if (listener != null) listener.sourceInfo(info);
			int step = 0;
			int lineNR = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				lineNR++;
				_log.debug("Input Line (" + lineNR + "): " + line);
				if (step == 0) if (line.startsWith("WARNING: ")) {
					if (listener != null) listener.message(line);
				} else if (line.startsWith("Output #0")) {
					step++;
				}
				if (step == 1) {
					if (line.startsWith("WARNING: ")) {
						if (listener != null) listener.message(line);
					} else if (line.startsWith("Output #0")) {
						step++;
					}
				} else if (step == 2 && !line.startsWith("  ")) {
					step++;
				}
				if (step == 3) {
					if (!line.startsWith("Stream mapping:")) throw new EncoderException(line);
					step++;
				} else if (step == 4 && !line.startsWith("  ")) {
					step++;
				}
				if (line.startsWith("frame=")) try {
					line = line.trim();
					if (line.length() > 0) {
						HashMap<String, String> table = parseProgressInfoLine(line);
						if (table == null) {
							if (listener != null) listener.message(line);
							lastWarning = line;
							continue;
						}
						if (listener != null) {
							String time = table.get("time");
							if (time != null) {
								String[] dParts = time.split(":");
								Double seconds = Double.valueOf(Double.parseDouble(dParts[0]) * 60.0D * 60.0D +
																Double.parseDouble(dParts[1]) * 60.0D +
																Double.parseDouble(dParts[2]));
								int perm = (int) Math.round(seconds.doubleValue() * 1000.0D * 1000.0D / duration);
								if (perm > 1000) perm = 1000;
								listener.progress(perm);
							}
						}
						lastWarning = null;
					}
				} catch (Exception ex) {
					_log.warn("Error in progress parsing for line: " + line);
				}
			}
			if (lastWarning != null && !SUCCESS_PATTERN.matcher(lastWarning).matches())
				throw new EncoderException("No match for: " + SUCCESS_PATTERN + " in " + lastWarning);
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
	}
}
